#!groovy

def DEV_EmailRecipients='amar.tyagi@kelltontech.com'
def QA_EmailRecipients='pratap.hada@kelltontech.com'
def MNGR_EmailRecipients='vijay.kumar@kelltontech.com'

def QA_BuildAuthorization='amar.tyagi@kelltontech.com' //vijay.kumar@kelltontech.com,
def PROD_BuildAuthorization='amar.tyagi@kelltontech.com'

node() {
try {

        stage ('Checkout'){
          checkout scm
        }

        stage ('Build')
        {
            // todo one time
            sh 'chmod a+x ./gradlew'
            sh './gradlew clean assembleRelease'
            if(currentBuild.previousBuild.result!=null && !currentBuild.previousBuild.result.toString().equals('SUCCESS'))
            {
                 sendEmails(DEV_EmailRecipients,'Hi,build succeded,after failure.','',false)
            }

        }

        stage ('Report'){
                sh './gradlew lint'
                androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint-results*.xml', unHealthy: ''
        }

        currentBuild.result='SUCCESS'
    }
    catch (Exception e)
    {
        currentBuild.result='FAILURE'
        sendEmails(DEV_EmailRecipients,'Hi,build Failed!','',true)
    }


    try {
        stage('Publish')
                {
                    String  branchName=env.BRANCH_NAME
                    if(branchName=='master')
                    {
                        //todo
                        timeout(time: 10, unit: 'SECONDS')
                                {
                                    echo" coming in timeout ${QA_BuildAuthorization} "
                                    input message: 'ready for manual testing(QA)?', submitter: "${QA_BuildAuthorization}"
                                    sendEmails(QA_BuildAuthorization,'Hi,Please find attached build for testing!','**/*.apk',false)
                                }


                    }
                    else if(branchName.startsWith('release'))
                    {

                    }

                }
    }
    catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException ee)
    {
        echo(" timout here ! build not published. ")
    }
    catch (Exception e)
    {
        sendEmails(DEV_EmailRecipients,'Hi,Publish failed !','',true)
    }


}

def sendEmails(emailRecipient,msg,pattern,logAttach) {

    emailext attachLog: logAttach,body: msg,attachmentsPattern:pattern, subject: '$PROJECT_NAME - Build # $BUILD_NUMBER -'+currentBuild.result, to:emailRecipient
}

  // stage ('upload')
  //      {
  //          try
  //          {
  //          androidApkUpload apkFilesPattern: '**/*.apk', googleCredentialsId: 'AmarExample', recentChangeList: [[language: 'es-US', text: 'New changes.']], trackName: 'alpha'
  //          }
  //          catch(Exception e)
  //          {
  //          sendEmails('''Hi,build not uploaded on play store , please see logs...''' +e.getMessage())
  //          }
  //      }*/

