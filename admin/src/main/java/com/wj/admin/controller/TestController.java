package com.wj.admin.controller;

import com.wj.core.repository.card.CardRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huzhou on 2019/7/15.
 */
//    @Log4j2
//    @RestController
//    @RequestMapping("/test/")
//    public  class TestController {
//
//        @Autowired
//        private CardRepository cardRepository;
//
//        @GetMapping("checking")
//        Object test(){
//           Integer count = cardRepository.exists(3, 1);
//            TestController.log.info("count:{}",count);
//           Integer count2 = cardRepository.exists(3, 100);
//           TestController.log.info("count2:{}",count2);
//            @Data
//            class TestInt{
//                Integer fisrt;
//                Integer second;
//
//                public TestInt(Integer fisrt, Integer second) {
//                    this.fisrt = fisrt;
//                    this.second = second;
//                }
//            }
//           return new TestInt(count,count2) ;
//       }
//
//
//}
