# == Definition: archive
#
# Download and extract an archive.
#
# Parameters:
#
# - *$url:
# - *$target: Destination directory
# - *$checksum: Default value "true"
# - *$digest_url: Default value ""
# - *$digest_string: Default value ""
# - *$digest_type: Default value "md5"
# - *$src_target: Default value "/usr/src"
# - *$root_dir: Default value ""
# - *$extension: Default value ".tar.gz"
# - *$timeout: Default value 120
# - *$allow_insecure: Default value false
# - *$follow_redirects: Default value false
# - *$verbose: Default value true
#
# Example usage:
#
#   archive {"apache-tomcat-6.0.26":
#     ensure => present,
#     url    => "http://archive.apache.org/dist/tomcat/tomcat-6/v6.0.26/bin/apache-tomcat-6.0.26.tar.gz",
#     target => "/opt",
#   }
#
define archive (
  $url,
  $target,
  $ensure=present,
  $checksum=true,
  $digest_url='',
  $digest_string='',
  $digest_type='md5',
  $timeout=120,
  $root_dir='',
  $extension='tar.gz',
  $src_target='/usr/src',
  $allow_insecure=false,
  $follow_redirects=false,
  $verbose=true,
) {

  archive::download {"${name}.${extension}":
    ensure           => $ensure,
    url              => $url,
    checksum         => $checksum,
    digest_url       => $digest_url,
    digest_string    => $digest_string,
    digest_type      => $digest_type,
    timeout          => $timeout,
    src_target       => $src_target,
    allow_insecure   => $allow_insecure,
    follow_redirects => $follow_redirects,
    verbose          => $verbose,
  }

  archive::extract {$name:
    ensure     => $ensure,
    target     => $target,
    src_target => $src_target,
    root_dir   => $root_dir,
    extension  => $extension,
    timeout    => $timeout,
    require    => Archive::Download["${name}.${extension}"]
  }
}
