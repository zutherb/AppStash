package io.github.appstash.shop.service.user.web

import io.github.appstash.shop.service.user.domain.User
import io.github.appstash.shop.service.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @Autowired
    def UserRepository userRepository;

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<User> users() {
        userRepository.findAll()
    }

    @RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    User userById(@RequestParam String id) {
        userRepository.findById(id)
    }

}
