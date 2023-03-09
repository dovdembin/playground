def getLabels(String text) {
	def pattern = /.*-l\s(.*)/
	if(text ==~ pattern) {
		def (word1) = text =~ pattern
		return  word1[1].toString().replaceAll(/\\\|/, ",")
	} else {
		return "noMatch"
	}
}