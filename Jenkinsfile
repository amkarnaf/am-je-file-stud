// ioi-ed-krndev01
// ioi-ep-socauto01
// /usr/lib/jvm/jdk-17-oracle-x64/lib/security/cacerts
// keytool -import -noprompt -trustcacerts -alias myFancyAlias -file /path/to/my/cert/myCert.cer -keystore /path/to/my/jdk/jre/lib/security/cacerts/keystore.jks -storepass changeit
// sudo keytool -import -noprompt -trustcacerts -alias virt-Ubu18 -file /home/alexm/ca/ioi-ed-krndev01_full.crt -keystore /usr/lib/jvm/jdk-17-oracle-x64/lib/security/cacerts -storepass changeit

println("==== (AM) Start script... ====");

pipeline
{
  agent none

  stages
  {
    stage("Build!")
    {
      steps
      {
        echo "Building..."
      }
    }
    stage("Test")
    {
      steps
      {
        echo "Testing..."
      }
    }
    stage("Deploy")
    {
      steps
      {
        echo "Deploying..."
      }
    }
  }
}

println("==== (AM) Stopp script... ====");
