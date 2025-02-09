# Introduction

What is it ? SSH is the Secure Shell, a popular powerful software based approach to network security. Whenever data is
sent by a computer to the network, SSH automatically encrypts it. When the data reaches its intended recipient, SSH
automatically decrypts it. The result is transparent encryption: users can work normally unaware that their
communications are safely encrypted on the network. In addition, SSH uses modern secure encryption algorithms and is
effective enough to be found within mission critical application at major corporations.

SSH has a client/server architecture. An SSH server program typically installed and run by a system administrator,
accepts or rejects incoming connections to its host computer. Users then run SSH client programs, typically on other
computers to make requests of the SSH server, such as - logging in, sending files, or executing commands. All
communication between the clients and the servers are security encrypted and protected from modification.

Our description is simplified but should give you a general idea of what SSH is and does. An SSH based product might
include clients, servers or both. Unix products generally container both clients and servers, those on other platforms
are usually just clients, though Windows based servers do exist.

If you are a Unix user, think of SSH as a secure form of the Unix r-commands, `rsb` (remote shell), `rlogin` (remote
login) and `rcp` (remote copy). In fact the original SSH for Unix includes the similarly named commands `ssb`, `scp` and
`slogin`, as secure, drop in replacements for the r-commands

## What SSH is NOT

Although SSH stands for Secure Shell, it is not a true shell in the sense of the Unix `Bourne shell` (bash) and `C
shell` (sh). It is not a command interpreter nor does it provide wildcard expansion command history and so forth.
Rather, SSH creates a channel for running a shell on a remote computer in the manner of the Unix `rsb` command. With
end-to-end encryption between the local and remote computer. SSH is also not a complete security solution - but then
again nothing is. It will not protect computers from active break-in attempts or denial of service attacks, and it wont
eliminate other hazards such as viruses. It does however provide a robust and user friendly encryption and
authentication.

## The SSH protocol

SSH is a protocol first and foremost, not a product, It is a specification of how to conduct secure communication over a
network. The SSH protocol covers authentication, encryption and the integrity of data transmitted over a network.

- Authentication - reliably determines someone's identity. If you try to log into an account on a remote computer, SSH
  asks for digital proof of your identify. If you pass the test, you may log in; otherwise SSH rejects the connection.

- Encryption - scrambles data so it is unintelligible expect to the intended recipient. This protects your data as it
  passes over the network.

- Integrity - guarantees the data traverling over the network arrives unaltered. If a third party captures and modifies
  you data in transit SSH detects this fact.

In short, SSH, makes network connections between computers with strong guarantees that the parties on both ends of the
connection are genuine. It also ensures that any data passing over these connections arrives unmodified and unread by
eavesdroppers

## Protocols, Products, Clients and Confusion

SSH based products are those that implement the SSH protocol, for many flavors of UNIX, Windows, Macintosh and other
operating systems. Bot freely distributable and commercial products are available. The first SSH product, was created
for Unix, was simply called SSH. This causes confusion because SSH is also the name of the protocol. Some people called
it Unix SSH, but other unix based implementations are not available so the name is not satisfactory.

## Overview of SSH features

As already mentioned SSH mostly revolves around secure login, data transmission and integrity

### Secure login

Let us suppose that we have accounts on several computers on the Internet. Typically you connect from a home PC to your
ISP, and then use a telnet program to log into your accounts on other computers. Unfortunately telnet transmits your
username and password in plaintext, over the internet, where a malicious third party can intercept them. Additionally
your entire telnet session is readable by a network snooper. SSH completely solves this problem. Rather than running the
insecure telnet program and protocol, you run the SSH program ssh, to login to the remote account or computer. This is
done with the following `ssh -l smith host.example.com -p 22`

The clients authenticates you to the remote computer's SSH server using an encrypted connection meaning that your
username and password are encrypted before they leave the local machine. The SSH server then logs you in and your entire
login session is encrypted as it travels between the client and server. Because the encryption is transparent you will
not notice any differences between telnet and the SSH client.

## Secure data transfer

Suppose you have two computers or machines on the internet and wish to transfer files between them, The file contains
trade secrets about your business, however that must be kept from prying eyes. A traditional file-transfer program such
as ftp, rcp or even email, does not provide a secure solution. A third party can intercept and read packets as it as
they travel over the network. To get around this problem you can encrypt the file on the first machine, with a program,
then send it over via traditional means, and decrypt the file on the second machine. Using SSH, the file can be
transferred, securely between the machines with a single secure copy command - `scp myfile user@host.com`. When
transmitted by scp the file is automatically encrypted as it leaves the first machine, and decrypted as it arrives on
the second machine

## Secure remote execution

Suppose you are a system admin who needs to run the same command on many computers. You would like to view the active
processes for each user on four different computers. On a local area network using the Unix command /usr/ucb/w.
Traditionally one could use `rsb`, assuming that the `rsb` daemon, `rsbd` is configured properly on the remote
computers. `rsh $machine /usr/usb/w`. This will work, however it is insecure, The results of executing the `/usr/usb/`
binary are transmitted over the network as plaintext. If you consider this information sensitive, the risk might be
unacceptable, Worse the `rsb` authentication mechanism is very insecure, and easily subverted. Instead using the `ssb`
command one can do - `ssb $machine /usr/ucb/w`. Where the `$machine` is a `sh` variable which represents the host name
of the machine to connect to, that could be a FQDN, or an IP address.

The syntax for both commands is nearly identical and the visible output is identical, but under the hood, the command
and its results are encrypted, sent, and then decrypted, and strong authentication techniques will be used to ensure
secure user login.

## Keys and agents

Suppose you have accounts on many computers on a network. For security reasons you prefer different passwords on all
accounts, but remembering so many passwords is difficult. It is also a security problem in itself. The more often you
type a password, the more likely you mistakenly type it in the wrong place. SSH has various authentication mechanisms
and the most secure is based on keys rather than passwords. Keys are a small blob of bits that uniquely identifies an
SSH user. For security a key is kept encrypted, and it may be used only after entering a secret pass phrase to decrypt
it.

Using keys together with a program called an authentication agent, SSH can authenticate you to all computers accounts
securely without requiring you to memorize many different passwords, or enter them repeatedly. The way it works is like
that:

- In advance (only once) you place files called public key files into the remote computers. These enable you to access
  the remote machines. These public keys identify `you as you` on the remote machines, you would later try to access

- On your local machine, invoke the ssh-agent program which runs in the background. This daemon will hold the keys `in
memory`, and only needs to be started once, when you login to your local machine for the day, this can be automated on
  most Unix systems, using `services`, or other tools such as `systemd`.

- Choose the key (or keys) you will need during your login session. These `have to correspond to the keys added in step
one` to the remote machines, they will be used to authenticate you when a connection is made.

- Load the keys into the agent, with the `ssh-add program`. This requires knowledge or each key's secret passphrase in
  order to load it into the ssh-agent daemon, this is however done only once and until the agent daemon works, the
  keys are loaded

At this point you have an ssh-agent program running on your local machine, holding your secret keys, in memory. You are
now done. You have password less access to all your remote accounts that contain your public key files. Say goodbye to
all the tedium of retyping passwords. The setup lasts until you log out from the local machine or terminate the
ssh-agent
