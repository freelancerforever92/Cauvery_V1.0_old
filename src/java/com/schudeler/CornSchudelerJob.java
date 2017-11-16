package com.schudeler;

import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CornSchudelerJob extends HttpServlet {

    private static final long serialVersionUID = 1L;
    static ResourceBundle rb = ResourceBundle.getBundle("dbConnection");

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            System.out.println("Method start");
            triggerCall();
        } catch (Exception e) {
            System.out.println("Error While Trigger" + e.getMessage());
        }
    }

    public static void triggerCall() {
        try {
          
            JobDetail job = JobBuilder.newJob(CornSchudelerTrigger.class).withIdentity(rb.getString("pos.job.name"), rb.getString("pos.scheduler")).build();
            //schedule it
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(rb.getString("pos.trigger.name"), rb.getString("pos.scheduler")).withSchedule(CronScheduleBuilder.cronSchedule(rb.getString("pos.scheduler"))).build();

            //schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
                System.out.println("EXCEPTION : "+e.getMessage());
        }
    }

    public static void main(String... args) {
        triggerCall();
    }
}
