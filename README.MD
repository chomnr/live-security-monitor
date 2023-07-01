![BruteExposeBanner](https://pouch.jumpshare.com/preview/lK7_PdZYLiMIPDsCzupUPPOxsIihH80ih1hY4Mw87nPT5eUrYSzU36ryz_VzTFk4CYVsYBGImTBJ2m6PE1p2UjJprjKJn4snkOQIlHyX-Do)
----------------------------------------------------------------

BruteExpose is an application that simply logs any failed login attempts when someone tries to access your server. In terms of practicality, it is useful to keep this data to yourself and avoid public logging. However, using BruteExpose requires a modified version of OpenSSH that has the "Junk Password" feature disabled. This modification helps prevent timing-based attacks. If you intend to incorporate this into your security system, I highly recommend forking your own version of OpenSSH and adjusting the junk password settings accordingly
