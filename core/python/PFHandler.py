#!/usr/bin/python

"""
SocialGuru Confidential - 2016 SocialGuru All Rights Reserved 

Description: This module implements several methods 
to collect public figure information. 

"""
import os
import facebook
import requests
import urllib
import urlparse
import subprocess
import warnings

class PFHandler:
    oauth_args = ""
    oauth_curl_cmd = ""
    oauth_response = ""
    oauth_access_token =""
    facebook_graph = ""
    graph = ""

    def __init__(self):    
        # You'll need an access token here to do anything.  You can get a temporary one
        # here: https://developers.facebook.com/tools/explorer/
        # Trying to get an access token. Very awkward.
        self.oauth_args = dict(client_id     = os.environ['FACEBOOK_APP_ID'],
                               client_secret = os.environ['FACEBOOK_SECRET'],
                               grant_type    = 'client_credentials')
        self.oauth_curl_cmd = ['curl',
                               'https://graph.facebook.com/oauth/access_token?' + urllib.urlencode(self.oauth_args)]
        self.oauth_response = subprocess.Popen(self.oauth_curl_cmd,
                                               stdout = subprocess.PIPE,
                                               stderr = subprocess.PIPE).communicate()[0]
        try:
           self.oauth_access_token = urlparse.parse_qs(str(self.oauth_response))['access_token'][0]
        except KeyError:
            print('Unable to grab an access token!')
            exit()

        self.facebook_graph = facebook.GraphAPI(self.oauth_access_token)
        self.graph = facebook.GraphAPI(self.oauth_access_token)

    def some_action(self,post):
        """ Here you might want to do something with each post. E.g. grab the
        post's message (post['message']) or the post's picture (post['picture']).
        In this implementation we just print the post's created time.
        """
        print(post['created_time'])


    def general_info(self,fbpf):
        """ This subroutine executes a GET request and fetches the general information from the 
        given username passed as a paramteter. For example: 
        Input: general_info('cosmosmidnight')
        Output: jSON format file containing the general information about the given public figure:
        Name, ID, about, fan_count, likes, app links (if any), etc... 
        """
        try:
          profile = self.graph.get_object(fbpf)
          posts = self.graph.get_connections(profile['id'], 'posts')
          profile_data = self.graph.get_connections(profile['id'],'?fields=id,name,fan_count,about,likes,events')
        except NameError:
          print('The username is incorrect')
        
        #print profile_data
        #print profile_data['fan_count']
        return profile_data

  
print "PFHandler.__doc__:", PFHandler.__doc__
print "PFHandler.__name__:", PFHandler.__name__
print "PFHandler.__module__:", PFHandler.__module__
print "PFHandler.__bases__:", PFHandler.__bases__
print "PFHandler.__dict__:", PFHandler.__dict__

"""
# Wrap this block in a while loop so we can keep paginating requests until
# finished.
while True:
    try:
        # Perform some action on each post in the collection we receive from
        # Facebook.
        [some_action(post=post) for post in posts['data']]
        # Attempt to make a request to the next page of data, if it exists.
        posts = requests.get(posts['paging']['next']).json()
    except KeyError:
        # When there are no more pages (['paging']['next']), break from the
        # loop and end the script.
        break"""
