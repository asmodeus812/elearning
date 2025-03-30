#!/bin/sh

ab -n 512 -c 16 -p posta -T "application/x-www-form-urlencoded" http://vote/
ab -n 512 -c 16 -p postb -T "application/x-www-form-urlencoded" http://vote/
ab -n 512 -c 16 -p posta -T "application/x-www-form-urlencoded" http://vote/
