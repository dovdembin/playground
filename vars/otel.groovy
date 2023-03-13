def getLabels(String text, String appliance) {
	
	def pattern = /.*\s-l\s(.*)/
	if(text ==~ pattern) {
		def (word1) = text =~ pattern
		def lblList = word1[1].toString().replaceAll(/\\\|/, ",").trim()
	} else {
		return "noMatch"
	}

	def res = sh(script:"""
							curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=${appliance}' \
							--header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | \
							jq -r -j '.objects[] | .tags + \",\" + .generation.name'
						""", returnStdout: true, label: "xpool_allocation")

	ArrayList ljLabels = res.split(",")
	ArrayList listlabes = lblList.split(",")
    return listlabes.intersect(ljLabels)
}

