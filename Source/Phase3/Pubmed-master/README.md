Pubmed
======

This code makes search on PUBMED using ENTREZ Rest API and retrieves the PubMed Id and abstracts as search results.
As second step, it checks whether there is any PVT and Brain related keywords inside the abstracts by using regular expressions. 

Output : 
1. It stores the abstracts as text files with PubMed IDs given as name.
2. It stores a PublicationList.csv which includes whether the given patterns are included or not for each publication
