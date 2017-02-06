node() {
stage ('Checkout'){
  checkout scm
}

stage ('Build'){
    sh 'chmod a+x ./gradlew'
    sh './gradlew clean assembleRelease'
    emailext body: '''Hi,
    Jenkins try to make a build.''', subject: 'Jenkins example', to: 'amar.tyagi@kelltontech.com'
}
stage ('Report'){
        sh './gradlew lint'
        androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint-results*.xml', unHealthy: ''
}
}