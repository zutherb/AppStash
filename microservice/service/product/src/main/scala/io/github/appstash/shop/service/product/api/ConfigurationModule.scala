package io.github.appstash.shop.service.product.api

import io.github.appstash.shop.service.product.model.ServiceConfiguration

trait ConfigurationModule {

  type Configuration <: ConfigurationLike

  def Configuration(): Configuration

  trait ConfigurationLike {
    def get() : ServiceConfiguration
  }

}
