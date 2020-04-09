import org.rmc33.lernaJenkins.Utilities

def call(script) {
    println "default..."
}

def call(script, closure) {
    def config = [:]

    closure.delagate = config
    closure()

    def scriptPath

    for (e in config.branchMapping) {
        println "branch name ${env.BRANCH_NAME}"
        if (env.BRANCH_NAME.startsWith(e.key)) {
            scriptPath = e.value.path
        }
    }

    node {
        checkout scm: [$class: 'GitSCM', branches: [[name: env.BRANCH_NAME]], extensions: [],  userRemoteConfigs: [[credentialsId: '6ff57ef9-fbd3-43ca-9fec-24277c97785f', url: 'https://github.com/rmc33/lernaJenkins.git']]]
        println "loading class ${env.WORKSPACE}/${scriptPath}"
        def branchScript = this.class.classLoader.parseClass("${env.WORKSPACE}/${scriptPath}").newInstance()
        def changedPackages = branchScript.getChangedPackages(script)
        changedPackages.each { packageName ->
            branchScript.runPipeline(script, packageName)
        }
    }
}