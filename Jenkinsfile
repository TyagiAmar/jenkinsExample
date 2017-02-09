#!groovy

def DEV_EmailRecipients='amar.tyagi@kelltontech.com'
def QA_EmailRecipients='pratap.hada@kelltontech.com'
def MNGR_EmailRecipients='vijay.kumar@kelltontech.com'

def QA_BuildAuthorization='amar.tyagi@kelltontech.com'
def PROD_BuildAuthorization='vijay.kumar@kelltontech.com'


// Constants variable used below for msg
def BUILD_SUCCESS_AFTER_FAILED='Hi, Build process completed successfully after failure last build!'
def BUILD_FAILED='Hi, Build process failed! Check attached log file.'
def BUILD_PUBLISH_QA_STAGE_SUCCESS='Hi, Build successfully published to given recipients.'
def BUILD_PUBLISH_FAILED='Hi, Build publish failed, please check attached log file.'

node() {
    String branchName = env.BRANCH_NAME
    echo" branch name "+branchName
    try {
            stage ('Checkout'){
              checkout scm
            }

            stage ('Build')
            {
                // todo one time
                sh 'chmod a+x ./gradlew'
                if(branchName.startsWith('release') || branchName.startsWith('feature'))
                    sh './gradlew clean assemblerelease'
                else
                    sh './gradlew clean assembledebug'
                if(currentBuild.previousBuild.result!=null && !currentBuild.previousBuild.result.toString().equals('SUCCESS'))
                {
                     sendEmails(DEV_EmailRecipients,BUILD_SUCCESS_AFTER_FAILED,'',false)
                }
            }

           // stage ('Report'){
          //          sh './gradlew lint'
          //          androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint-results*.xml', unHealthy: ''
          //  }

            currentBuild.result='SUCCESS'
        }
        catch (Exception e)
        {
            currentBuild.result='FAILURE'
            echo" failure exceoption "+e.toString()
            sendEmails(DEV_EmailRecipients,BUILD_FAILED,'',true)
        }

        if( currentBuild.result=='SUCCESS') {
            try {
                stage('Publish')
                        {
                            if (branchName == 'develop') {
                                //todo
                                timeout(time: 120, unit: 'SECONDS')
                                        {
                                            echo " coming in timeout "
                                            //input message: 'ready for manual testing(QA)?', submitter: "${QA_BuildAuthorization}"
                                            //def result=input message: 'Proceed with release?', parameters: [choice(choices=["Stage", "Prod"], description: '', name: 'BuildFlavour')]
                                            //
                                            def outcome = input id: 'Please take a action',
                                                    message: 'Workflow Configuration',
                                                    ok: 'Okay',
                                                    parameters: [
                                                            [
                                                                    $class: 'BooleanParameterDefinition',
                                                                    defaultValue: false,
                                                                    name: 'Send build to QA?',
                                                                    description: 'provide answer'
                                                            ]
                                                    ]

                                            echo" ans "+outcome

                                            //

                                           // echo "input return value ---------->>>>>>>> "+result

                                            sendEmails(DEV_EmailRecipients,BUILD_PUBLISH_QA_STAGE_SUCCESS, '**/*.apk', false)
                                        }
                            } else if (branchName.startsWith('release')) {
                                // todo do release code here
                                sendEmails(DEV_EmailRecipients+" "+QA_EmailRecipients+" "+MNGR_EmailRecipients,BUILD_PUBLISH_QA_STAGE_SUCCESS, '**/*.apk', false)
                            }

                        }
            }
            catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException ee) {
                echo(" timeout here ! build not published. ")
            }
            catch (Exception e) {
                echo" publish exception "+e.toString()
                sendEmails(DEV_EmailRecipients, BUILD_PUBLISH_FAILED, '', true)
            }
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

