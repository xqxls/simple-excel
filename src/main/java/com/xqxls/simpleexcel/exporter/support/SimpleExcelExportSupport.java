package com.xqxls.simpleexcel.exporter.support;

import com.alibaba.excel.EasyExcel;
import com.xqxls.simpleexcel.SimpleExcelContext;
import com.xqxls.simpleexcel.common.ErrorMessage;
import com.xqxls.simpleexcel.common.support.SimpleExcelTaskCalculateSupport;
import com.xqxls.simpleexcel.common.support.SimpleExcelTaskHandleSupport;
import com.xqxls.simpleexcel.exporter.handler.SimpleExcelExportChainContext;
import com.xqxls.simpleexcel.feign.FileDTO;
import com.xqxls.simpleexcel.feign.FileInfoControllerFeign;
import com.xqxls.simpleexcel.persist.dao.SimpleExcelTaskMapper;
import com.xqxls.simpleexcel.util.FileUtils;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/4/25
 */
public class SimpleExcelExportSupport extends SimpleExcelTaskHandleSupport {


    private SimpleExcelExportChainContext context;

    private SimpleExcelExportDataSupport exportDataSupport;

    private SimpleExcelTaskCalculateSupport taskCalculateSupport;

    private FileInfoControllerFeign fileInfoFeign;

    public SimpleExcelExportSupport(SimpleExcelExportChainContext context, SimpleExcelTaskMapper taskMapper, FileInfoControllerFeign fileInfoFeign) {
        super(context, context.getTaskCalculateSupport(), taskMapper);
        this.context = context;
        this.exportDataSupport = context.getExportDataSupport();
        this.taskCalculateSupport = context.getTaskCalculateSupport();
        this.fileInfoFeign = fileInfoFeign;
    }

    @Override
    public void singleHandleBefore() {

    }

    @Override
    public void singleHandleAfter(List handList) {

        //1、任务处理
        recordCount(handList);

        //2、写入文件处理 （响应给客户端、异步下载到文件中心）
        exportDataSupport.handleDataList(handList);

    }

    private void recordCount(List handList) {
        if (CollectionUtils.isEmpty(handList)) {
            return;
        }
        //记录数量
        taskCalculateSupport.calculateCount(handList.size(), handList.size(), 0);
    }


    @Override
    protected void init0() {
        //1、计算总数量
        if (context.getCurTask() != null && context.getHandle() != null) {
            context.getCurTask().setEstimateCount(context.getHandle().fetchTotalCount());
        }
    }


    @Override
    @SneakyThrows
    public void onException(Exception exception) {

        exportDataSupport.resetResponse();

        if (curTask != null) {
            //写入错误数据
            File errorFile = buildTemporaryFileOnException();
            context.setErrorFile(errorFile);
            ErrorMessage error = ErrorMessage.error(null, ExceptionUtils.getStackTrace(exception));
            //简要错误入库
            curTask.getInnerFailedMessageList().add(ErrorMessage.error(null, exception.toString()));
            //快速写入
            EasyExcel.write(errorFile, ErrorMessage.class)
                    .sheet(context.getSimpleExcelExportReq().getSheetName())
                    .doWrite(Arrays.asList(error));
        }

        context.setLastProgramException(exception);

        //继续往上抛
        throw exception;
    }


    @Override
    @SneakyThrows
    protected void onFinally0() {

        //1、释放资源
        //1.1、上下文清理
        SimpleExcelContext.removeContext();

        //1.2、关闭写出流
        // 同步导出失败的时候不能关闭，因为这个时候需要返回json，如果关闭会将excel数据刷入response中
        if (context.getLastProgramException() == null || curTask != null) {
            context.getExcelWriter().finish();
        }


        if (curTask != null) {
            //2、文件最终写入 这里也可能报错
            if (context.getSuccessFile() != null) {
                File successFile = context.getSuccessFile();
                String fileName = context.getSuccessFileName();
                MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, null, new FileInputStream(successFile));
                FileDTO fileDto = fileInfoFeign.commonUpload("simple-excel-ex-success", multipartFile).getData();
                curTask.setSuccessFileId(fileDto.getId());
                curTask.setSuccessFileName(fileName);
                //删除文件
                context.getSuccessFile().delete();
            }


            //2、文件最终写入 这里也可能报错
            if (context.getErrorFile() != null) {
                File errorFile = context.getErrorFile();
                String fileName = context.getErrorFileName();
                MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, null, new FileInputStream(errorFile));
                FileDTO fileDto = fileInfoFeign.commonUpload("simple-excel-ex-error", multipartFile).getData();
                curTask.setFailedFileId(fileDto.getId());
                curTask.setFailedFileName(fileName);
                //删除文件
                context.getErrorFile().delete();
            }
        }


    }


    @SneakyThrows
    protected File buildTemporaryFileOnException() {
        StringBuffer sb = new StringBuffer();
        String fileNameParam = context.getSimpleExcelExportReq().getFilename();
        if (fileNameParam.lastIndexOf('.') != -1) {
            fileNameParam = fileNameParam.substring(0, fileNameParam.indexOf('.'));
        }
        sb.append(fileNameParam);
        sb.append("_导出失败");
        sb.append(context.getStartTime()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        sb.append(".xlsx");
        context.setErrorFileName(sb.toString());
        return FileUtils.buildFileTemporaryFileName(sb.toString());
    }


}
