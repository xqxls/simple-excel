package com.xqxls.simpleexcel.common.support;

import com.xqxls.simpleexcel.common.base.ctx.SimpleExcelBaseChainContext;
import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @USER: xqxls
 * @DATE: 2023/4/26
 */
@Slf4j
public class SimpleExcelTaskCalculateSupport {

    protected SimpleExcelTask curTask;


    /**
     * 最新记录的数量
     */
    private Long lastTotalCount;


    /**
     * 最新的计算时间
     */
    private Long lastCalculateTime;


    public SimpleExcelTaskCalculateSupport(SimpleExcelBaseChainContext context) {
        this.curTask = context.getCurTask();
    }


    /**
     * 计算数量
     *
     * @param totalCountIcr
     * @param successCountIcr
     * @param failedCountIcr
     */
    public void calculateCount(Integer totalCountIcr, Integer successCountIcr, Integer failedCountIcr) {
        if (this.curTask != null) {
            this.curTask.setTotalCount(this.curTask.getTotalCount() + totalCountIcr);
            this.curTask.setSuccessCount(this.curTask.getSuccessCount() + successCountIcr);
            this.curTask.setFailedCount(this.curTask.getFailedCount() + failedCountIcr);
        }
    }

    /**
     * 计算剩余完成时间
     */
    public void calculateRemainingCompletionTime() {

        if (this.curTask != null && this.curTask.getEstimateCount() != null) {

            long now = System.currentTimeMillis();

            if (lastCalculateTime != null) {
                //处理数量差值
                Long diffTotalCount = this.curTask.getTotalCount() - lastTotalCount;
                if (diffTotalCount <= 0) {
                    return;
                }
                //处理时间差值
                Long diffCalculateTime = now - lastCalculateTime;

                //换算成秒
                Double secondInterval = ((double) diffCalculateTime) / 1000L;

                //计算现在的每秒处理速度 至少是1秒
                Double countPerSecond = Math.ceil(diffTotalCount / secondInterval);

                //总剩余数量
                Long remainCount = this.curTask.getEstimateCount() - this.curTask.getTotalCount();

                if (remainCount > 0) {
                    Double remainingCompletionTime = remainCount / countPerSecond;
                    this.curTask.setRemainingCompletionTime(remainingCompletionTime.intValue());
                }
            }

            //保存本次处理时间
            lastTotalCount = this.curTask.getTotalCount();
            lastCalculateTime = now;

        }

    }


}
