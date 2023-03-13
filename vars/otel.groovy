def getLabels(String text) {
	// def pattern = /.*\s-l\s(.*)/
	// if(text ==~ pattern) {
	// 	def (word1) = text =~ pattern
	// 	def lblList = word1[1].toString().replaceAll(/\\\|/, ",").trim()
	// 	ArrayList listlabes = labels.split(",")
		def cmd = "curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=WK-D0046' --header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | jq -r -j '.objects[] | .tags + ',' + .generation.name'"
		def res = sh(script: cmd, returnStdout: true, label: "xpool_allocation")
	// 	ArrayList ljLabels = res.split(",")
	// 	return listlabes.intersect(ljLabels)
	// } else {
	// 	return "noMatch"
	// }
	 
}