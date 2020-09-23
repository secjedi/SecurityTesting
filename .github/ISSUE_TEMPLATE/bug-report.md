---
name: Bug report
about: Bug report on failed test cases
title: Add htmlentities to validate input
labels: bug
assignees: secjedi

---

**Bug Desctiption**
htmlentities missing in line 13. Hence XSS is possible on the 'editOrderPage'

**To Reproduce**
Steps to reproduce the behavior:
1. Go to the 'indexpage'
2. Click on 'Order' menu
3. Scroll down to any order of your preference and click 'edit order'
4. Fill in the necessary fields with xss payload; phone number, name, payment method, etc.
5. See error

**Expected behavior**
Go back to the order page and notice xss payload is visible

**Screenshots**
Screenshots of failure can be found in res/src/screenshots/editorder_fail.

**Desktop**
 - Linux
 - Chrome 80.0.1
 - 20.04

**Additional context**
Please add 'htmlentities' to validate input
