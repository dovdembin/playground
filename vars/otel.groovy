import java.util.regex.Pattern


def getLabels(String labels) {
	if(labels.isEmpty()) {
		def pattern = ~".*-l(.*)"
		def matcher = labels =~ pattern
		return matcher[0][1].toString().replace("\\|", ",").trim();
	}
}