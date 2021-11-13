//package com.manish.ipl.batch;
//
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.job.flow.FlowExecutionStatus;
//import org.springframework.batch.core.job.flow.JobExecutionDecider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHandler;
//import org.springframework.messaging.MessagingException;
//
//public class Decider implements JobExecutionDecider {
//	
////	@Autowired
////	MessageHandler handler;
//	String status = "pause";
//	
//	@ServiceActivator(inputChannel = "fileInputChannel")
//    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
//        
//        
//        MessageHandler messageHandler = new MessageHandler() {
//			
//			@Override
//			public void handleMessage(Message<?> message) throws MessagingException {
//				if(message.getPayload()!= null) {
//					status = "START";
//				}
//			}
//		};
////        (m) -> {
//////        if(handler.)
////        if (someCondition()) {
////            status = "FAILED";
////        }
////        else {
////            status = "COMPLETED";
////        }
//////        return new FlowExecutionStatus(status);
////    }
//        
//        if(status.equalsIgnoreCase("START") && status != null) {
//        	return new FlowExecutionStatus(status);
//        }
//	}
//}