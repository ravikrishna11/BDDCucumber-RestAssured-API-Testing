@FunctionalTest
Feature: Library Management


@Book_details
Scenario Outline: Creating Books Information
Given Create Book entry with "<bookName>","<authorId>","<publisherId>","<authorName>","<publisherName>","<genre>","<price>","<numberOfPages>"
Then Get books details using Book Id
Then Get books details by PublisherId 
Then Get books deatails by authorId 
#Then Delete the book deatail by Book Id

Examples: 
| bookName | authorId | publisherId | authorName | publisherName | genre  | price | numberOfPages |  
| Maths    | 1        | 1           | Rapo       | ram           | numbers| 199   | 150           | 



@Publisher_details
Scenario Outline: Creating the Publisher
Given Create the post method for publisher "<publisherName>","<address>","<country>"
Then Delete the publisher by using publisherId

Examples:
| publisherName | address   | country |
| publisherone  | Bangalore | India   |

@Author_details
Scenario Outline: Creating the Author
Given Creating the author "<name>","<address>","<country>"
Then Delete the author by authorId

Examples:
|  name     |  address   |  country  |
| authorone |  Hyderabad |  India    |


@Customer_Booking
Scenario Outline: Create Customer and Booking
Then Create the customer "<name>","<address>","<country>"
Then Create the booking
Then Create the SaveBooking
Then Create the saveBilling "<billingAddress>","<bookingCost>","<zipzode>","<state>"
Then Verify number of books hold by customer

Examples:
| name   | customerId | address | country | billingAddress | bookingCost | zipzode | state     |
| sachin | 1          | mumbai  | india   | Hyderabd       | 100         | 1234    | Telangana |
