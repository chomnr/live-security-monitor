![BruteExposeBanner](https://pouch.jumpshare.com/preview/lK7_PdZYLiMIPDsCzupUPPOxsIihH80ih1hY4Mw87nPT5eUrYSzU36ryz_VzTFk4CYVsYBGImTBJ2m6PE1p2UjJprjKJn4snkOQIlHyX-Do)
----------------------------------------------------------------

BruteExpose is an application that logs any failed login attempts when someone tries to access your server. It also includes its own metrics and analytics system, allowing you to easily add or remove any metrics or analytics as needed.

#### Notice
In terms of practicality, it is useful to keep this data to yourself and avoid public logging. However, using BruteExpose requires a modified version of OpenSSH that has the "Junk Password" feature disabled. This modification helps prevent timing-based attacks. If you intend to incorporate this into your security system, I highly recommend forking your own version of OpenSSH and adjusting it accordingly.

## Getting Started

### 1. Linux prerequisites
```
sudo apt update

// Required
sudo apt install build-essential zlib1g-dev libssl-dev
sudo apt install libpam0g-dev libselinux1-dev libkrb5-dev
sudo apt install autoconf

// Optional
sudo apt install checkinstall
```
### 2. Requirements (Main)
1. Install [OpenJDK 19](https://github.com/corretto/corretto-19/releases)
2. Install [CMake 3.25.1](https://github.com/Kitware/CMake/releases/download/v3.25.1/cmake-3.25.1.tar.gz)
3. Install [BruteExpose OpenSSH](https://github.com/chomnr/openssh-portable.git)
4. Add [BruteExpose PAM Module](https://github.com/chomnr/be-pam)
5. Add [BruteExpose](https://github.com/chomnr/BruteExpose/releases)

### 3. Setting up CMake
```
wget https://github.com/Kitware/CMake/releases/download/v3.25.1/cmake-3.25.1.tar.gz
tar -xzvf cmake-3.25.1.tar.gz
cd cmake-3.25.1
./bootstrap
sudo make

sudo make install
OR
sudo checkinstall 
```

### 4. Setting up OpenSSH
```
git clone https://github.com/chomnr/openssh-portable
cd openssh-portable
autoreconf
./configure --with-kerberos5 --with-pam --with-privsep-path=/var/lib/sshd/ --sysconfdir=/etc/ssh
sudo make
sudo make install
```

### 4b. Switch OpenSSH for client.
```
cd /lib/systemd/system/ssh.service

ExecStartPre=/usr/sbin/sshd -t
ExecStart=/usr/sbin/sshd -D $SSHD_OPTS
ExecReload=/usr/sbin/sshd -t

to

ExecStartPre=/usr/local/sbin/sshd -t
ExecStart=/usr/local/sbin/sshd -D $SSHD_OPTS
ExecReload=/usr/local/sbin/sshd -t

more information here
https://stackoverflow.com/questions/28860802/needed-packages-to-run-autoreconf-and-configure-on-debian-wheezy
```

### 5. Adding & Setting up PAM Module

#### Adding
All the pam modules should be under <b>/lib/x86_64-linux-gnu/security/</b>.
So just drag and drop the bruteexpose pam module <b>libbe_pam.so</b> there.
#### Setting up
```
sudo nano /etc/pam.d/common-auth
```
 from
```
sudo nano /etc/pam.d/common-auth

original /etc/pam.d/common-auth
# here are the per-package modules (the "Primary" block)
auth    [success=2 default=ignore]      pam_unix.so nullok
# here's the fallback if no module succeeds
auth    requisite                       pam_deny.so
```
 to
```
# here are the per-package modules (the "Primary" block)
auth    [success=2 default=ignore]      pam_unix.so nullok
# enable BruteExpose.
auth    optional                        libbe_pam.so
# here's the fallback if no module succeeds
auth    requisite                       pam_deny.so
```

What's happening? If pam.unix.so is successfully it will skip the next 2 lines. If not
it will hit our pam module then the pam_deny module.

## Running BruteExpose
After everything is set up locate BruteExpose.jar and run.
```
java -jar /path/to/your.jar

to run in the background
nohup java -jar myprogram.jar &
```

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