![BruteExposeBanner](https://pouch.jumpshare.com/preview/lK7_PdZYLiMIPDsCzupUPPOxsIihH80ih1hY4Mw87nPT5eUrYSzU36ryz_VzTFk4CYVsYBGImTBJ2m6PE1p2UjJprjKJn4snkOQIlHyX-Do)
----------------------------------------------------------------

BruteExpose is an application that logs any failed login attempts when someone tries to access your server. It also includes its own metrics and analytics system, allowing you to easily add or remove any metrics or analytics as needed.

#### Notice
In terms of practicality, it is useful to keep this data to yourself and avoid public logging. However, using BruteExpose requires a modified version of OpenSSH that has the "Junk Password" feature disabled. This modification helps prevent timing-based attacks. If you intend to incorporate this into your security system, I highly recommend forking your own version of OpenSSH and adjusting it accordingly.

## Getting Started
#### Requirements
1. [OpenJDK 19](https://github.com/corretto/corretto-19/releases)
2. [CMake 3.25.1](https://github.com/Kitware/CMake/releases/download/v3.25.1/cmake-3.25.1.tar.gz)
3. [BruteExpose OpenSSH](https://github.com/chomnr/openssh-portable.git)
4. [BruteExpose PAM Module](https://github.com/chomnr/be-pam)
5. [BruteExpose](https://github.com/chomnr/BruteExpose/releases)

### 1. Linux prerequisites
```bash
sudo apt update

// Required
sudo apt install build-essential zlib1g-dev libssl-dev
sudo apt install libpam0g-dev libselinux1-dev libkrb5-dev
sudo apt install autoconf

// Optional
sudo apt install checkinstall
```

### 2. Setting up CMake
```bash
wget https://github.com/Kitware/CMake/releases/download/v3.25.1/cmake-3.25.1.tar.gz
tar -xzvf cmake-3.25.1.tar.gz
cd cmake-3.25.1
./bootstrap
sudo make

sudo make install
OR
sudo checkinstall 
```

### 3. Setting & installing up OpenSSH
```bash
git clone https://github.com/chomnr/openssh-portable
cd openssh-portable
autoreconf
./configure --with-kerberos5 --with-pam --with-privsep-path=/var/lib/sshd/ --sysconfdir=/etc/ssh
sudo make
sudo make install

//why we need to do this
https://superuser.com/questions/900417/pam-exec-so-doesnt-write-password-to-script-when-expose-authtok-is-enabled
```

### 3b. Switch OpenSSH for client.
```bash
cd /lib/systemd/system/ssh.service

ExecStartPre=/usr/sbin/sshd -t
ExecStart=/usr/sbin/sshd -D $SSHD_OPTS
ExecReload=/usr/sbin/sshd -t

to

ExecStartPre=/usr/local/sbin/sshd -t
ExecStart=/usr/local/sbin/sshd -D $SSHD_OPTS
ExecReload=/usr/local/sbin/sshd -t

more information here
https://askubuntu.com/questions/1341247/openssh-showing-different-version-from-installed-version-on-remote-connection
```

### 4. Adding & Setting up PAM Module

#### Adding
All the pam modules should be under <b>/lib/x86_64-linux-gnu/security/</b>.
So just drag and drop the bruteexpose pam module <b>libbe_pam.so</b> there.
#### Setting up
```bash
sudo nano /etc/pam.d/common-auth
```
 from
```bash
sudo nano /etc/pam.d/common-auth

original /etc/pam.d/common-auth
# here are the per-package modules (the "Primary" block)
auth    [success=2 default=ignore]      pam_unix.so nullok
# here's the fallback if no module succeeds
auth    requisite                       pam_deny.so
```
 to
```bash
# here are the per-package modules (the "Primary" block)
auth    [success=2 default=ignore]      pam_unix.so nullok
# enable BruteExpose.
auth    optional                        libbe_pam.so
# here's the fallback if no module succeeds
auth    requisite                       pam_deny.so
```

What's happening? If pam.unix.so is successful it will skip the next 2 lines. If not
it will hit our pam module then the pam_deny module.

## Running BruteExpose
After everything is set up locate BruteExpose.jar and run.
```bash
java -jar /path/to/your.jar

to run in the background
nohup java -jar myprogram.jar &
```
If you get a FileNotFoundException. You need to manually make the following files <br>
in the appropriate directory.<br><br>
METRIC_FILE,
TRACKER_FILE,
LOG_FILE
<br><br>
refer to Constants.Java for their names & directories.


## Analytics
* NumberOfAttemptsOverTime (Hourly, Daily & Weekly)
* AttackTotalByDayOfWeek
* DistributionOfAttackProtocols
* AttackOriginByCountry
* AttackOriginByIp
* CommonlyTargetedByCredential

## Contributions
I will accept contributions from anyone. Some things you can add
* More metrics or analytics
* Add more abstraction to BruteFileListener
* Maybe bugs?