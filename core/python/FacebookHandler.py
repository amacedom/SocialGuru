#!/usr/bin/python

"""
SocialGuru Confidential - 2016 SocialGuru All Rights Reserved 
Description: This module contains the necessary functions to
collect data from facebook 
"""

import os
import facebook
import requests
import urllib
import urlparse
import subprocess
import warnings

""" Class Name: facebookHandler
*** In: Name: username Type: <String>
*** Out: Name: None Type: <facebookHandler object> """
class facebookHandler:
    oauthAccessToken = ""
    oauthCurlCmd = ""
    oauthResponse = ""
    facebookGraph = ""
    oauthArgs = ""
    graph = ""
    username=""
    fanCount=""


    """ Description: Class Constructor 
    *** In: Name: username Type: 
    *** Out: Name: self Type: facebookHandler """
    def __init__(self, username):
    	if os.environ['FACEBOOK_APP_ID'] and os.environ['FACEBOOK_SECRET'] in locals():
    		self.username = username
    		self.oauthArgs = dict( client_id     = os.environ['FACEBOOK_APP_ID'],
                               	   client_secret = os.environ['FACEBOOK_SECRET'],
                                   grant_type    = 'client_credentials')
        	
        	self.oauthCurlCmd = ['curl','https://graph.facebook.com/oauth/access_token?' + 
                                 urllib.urlencode(self.oauthArgs)]
        	
        	self.oauthResponse = subprocess.Popen( self.oauthCurlCmd,
                                               	   stdout = subprocess.PIPE,
                                                   stderr = subprocess.PIPE).communicate()[0]
        	try:
		    	self.oauthAccessToken = urlparse.parse_qs(str(self.oauthResponse))['access_token'][0]
        	except KeyError: 
			  	print('Error while attempting to connect using access token :(')
              	exit()
        
        	self.facebookGraph = facebook.graphAPI(self.oauthAccessToken)
        	self.graph = facebook.graphAPI(self.oauthAccessToken)
        	
        else:
        	print "Missing Environment Variables 'FACEBOOK_SECRET' and 'FACEBOOK_APP_ID'"
        	exit()

    """ Description: This subroutine executes a GET request and fetches the general information from the 
        given username passed as a paramteter. For example: 
    *** In: None
    *** out: Name fanCount Type: <String>"""
    def __getFanCount__(self):
    	return self.fanCount

    """ Description: This subroutine executes a GET request and fetches the general information from the 
        given username passed as a paramteter. For example: 
    *** In: None
    *** out: Name fanCount Type: <String>"""
    def __getUsername__(self):
    	return self.username

    """ Description: This subroutine executes a GET request and fetches the general information from the 
        given username passed as a paramteter. For example: 
    *** In: None
    *** out: Name fanCount Type: <String>"""
    def __getLikes__(self):
    	return self.
    	




