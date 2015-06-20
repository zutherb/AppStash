
require 'spec_helper'

describe 'zookeeper::host' do

    let(:title) { 'localhost' }
    let(:params) {{
      :id        => '1',
      :client_ip => '192.168.1.1',
      :election_port => 2222,
      :leader_port => 3333,
    }}

    it { should contain_datacat_fragment('localhost') }
    #it { should contain_datacat__collector('localhost') }

end

