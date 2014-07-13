package io.github.appstash.shop.service.user

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ThisWillActuallyRun {

    @RequestMapping("/")
    String home() {
        '''
          {bla : "blub"}
        '''
    }

}
