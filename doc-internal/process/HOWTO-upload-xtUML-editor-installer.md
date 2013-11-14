HOWTO Upload the xtUML Editor Installer to xtuml.org
-----------------------------------------------------
- Download CloudBerry Explorer (http://www.cloudberrylab.com/free-amazon-s3-explorer-cloudfront-IAM.aspx?ref=hpbanner)
  - NOTE: The app is already installed on kbrown-vm-w7-1
- Inside CloudBerry Explorer, create a new Amazon S3 account registration.  

>     When accessing the server through a FTP/S3 client:  
>     Server: s3.amazonaws.com  
>     User Name: xtuml-uploads  
>     Access Key: AKIAILIQWGFANPBE5LPQ  
>     Secret Key: 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  

- Upload files
- On each newly uploaded file, Right-click > Properties. Open the Security tab. Click on "All Users" and check "Read" 
- Update xtuml.org downloads page.  Move the Current release links to Prior release links section.  Add new current
  release links that point at the files that were just uploaded.  Add new MD5 sums.
  
  
  
Note:  
- Instead of using CloudBerry Explorer, you may be able to use the AWS web console with this access info:  

>     URL: https://console.aws.amazon.com/s3/home  
>     User Name: xtuml-uploads  
>     Password: Hs9^m#|AiO]J  
 



