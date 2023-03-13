def getLabels(String text) {
	def pattern = /.*\s-l\s(.*)/
	if(text ==~ pattern) {
		def (word1) = text =~ pattern
		return  word1[1].toString().replaceAll(/\\\|/, ",").trim()
	} else {
		return "noMatch"
	}
}
