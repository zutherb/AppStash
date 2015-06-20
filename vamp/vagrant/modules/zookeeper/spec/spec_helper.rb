require 'puppet'
require 'rspec'
require 'rubygems'
require 'puppetlabs_spec_helper/module_spec_helper'
require 'rspec-puppet/coverage'

fixture_path = File.expand_path(File.join(__FILE__, '..', 'fixtures'))

RSpec.configure do |c|
  c.module_path = File.join(fixture_path, 'modules')
  c.manifest_dir = File.join(fixture_path, 'manifests')
  # we don't want to run tests from submodules in fixtures/cron/..
  c.pattern = "spec/*/*_spec.rb"
  c.default_facts = {
    :kernel          => 'Linux',
    :concat_basedir  => '/var/lib/puppet/concat',
  }
end

Puppet::Util::Log.level = :warning
Puppet::Util::Log.newdestination(:console)

at_exit { RSpec::Puppet::Coverage.report! }
