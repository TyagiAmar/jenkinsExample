node() {
stage ('Checkout'){
  checkout scm
}

stage ('Build'){
    sh ‘chmod a+x ./gradlew’
    sh './gradlew clean assembleRelease'
}
stage ('Report'){
        sh './gradlew lint'
        androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint-results*.xml', unHealthy: ''
}
}