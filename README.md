# play-module-auction

This app combines Auction and traditional Malay game named Tikam-Tikam together. The game consists a board of numbers, each has a hidden prize behind. It could be extremely expensive, or almost valueless. No one knows what they are bidding for, but the list of prizes will be published before the event to keep everyone's hope high.

This project is created to facilitate Citibank Singapore's Charity Golf event in 2016.

### Requirements

* Java 1.8
* [SBT](https://www.scala-sbt.org/download.html)

### How to run locally

* Clean up old entries in <project_dir>/conf/ticket.log
* Open Command Prompt
* Go to project directory
* `sbt`
* `run`
* Open browser
	* `localhost:9000/form`: Bid submission page
    * `localhost:9000`: Interim result page - auto refresh every 5s
	* `localhost:9000/finalResults`: Final result page