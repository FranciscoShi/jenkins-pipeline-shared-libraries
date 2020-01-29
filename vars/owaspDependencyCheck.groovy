def call(Map args = [:], String... scanDirs) {
    args = [tool: "owasp-dependency-check-5.2.4"] << args
    withEnv(["PATH+DC=${tool name: args.tool}/bin"]) {
        sh 'mkdir -p data report'
        scanArgs = scanDirs.collect { "--scan '$it'"}.join(' ')
        sh "dependency-check.sh ${scanArgs} --format 'ALL' --out report ${args.extraArgs}"
    }
    dependencyCheckPublisher pattern: 'report/dependency-check-report.xml'
}
