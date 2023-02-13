 


def countTestOTel(duration, end_status) {
     
}

try {
 countTestOTel(duration, end_status)
} catch (Exception e) {
        println("ERROR: UNEXPECTED Jenkins FAILURE, marking build as FAILED")
        println(e.toString())
        currentBuild.result = 'FAILED'
        withEnv(env_list) {
            updateGitHubAndDBAfterUnexpectedFailure()
        }
    }