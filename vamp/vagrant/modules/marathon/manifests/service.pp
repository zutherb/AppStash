# == Class: marathon::service
#
class marathon::service (
  $ensure = undef,
  $provider = undef,
) {

  service { 'marathon':
    ensure   => $ensure,
    provider => $provider,
  }
}
