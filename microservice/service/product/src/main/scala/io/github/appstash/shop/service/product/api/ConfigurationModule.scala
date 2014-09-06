package io.github.appstash.shop.service.product.api

import io.github.appstash.shop.service.product.model.SystemConfiguration

trait ConfigurationModule {

  type Configuration <: ConfigurationLike

  def Configuration(): Configuration

  trait ConfigurationLike {
    def get() : SystemConfiguration
  }

}
