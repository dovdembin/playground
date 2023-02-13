@Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.20.1', type='pom')


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