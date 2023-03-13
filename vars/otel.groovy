def getLabels(String text) {
	cmd ="curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=WK-D0115' --header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | jq '.objects[] | {tags}'"
	def res = sh(script: cmd, returnStdout: true, label: "xpool_allocation")
	res = res.trim().replace(dir:"dirName", includes:"*.*", token:"tokenName", value:"${value}")
	def str = res.split(',');
	for( String values : str )
      println(values);
	
}


// config_params       = ['xpoolAllocation':'--lg 1 -l indus1\\|indus2\\|indus3,PhysicalLG,powerActionSupported,@Indus_Not_Aligned']

// println getLabels(config_params['xpoolAllocation'])