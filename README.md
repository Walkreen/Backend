# Walkreen API Document

**version 1.0**

http://localhost:1998/v2/api-docs

Spring Boot Rest API

### Auth Controller

- *POST* \
/api/auth/token | reIssueToken : reissue token by refresh token

### Parking Controller

- *POST* \
/api/parking/makeLot | makeLot : make parking lot

- *POST* \
/api/parking/park | park : park a car on a space

- *GET* \
/api/parking/search/{floor} | search : get a floor of parking lot 

- *DELETE* \
/api/parking/unpark | unpark : unpark a car from a space

### User Controller

- *POST* \
/api/user/findId/{email} | findLoginId : find login id by email

- *POST* \
/api/user/login | login : login

- *POST* \
/api/user/reset | resetPassword : reset Password and send email with temporary password

- *POST* \
/api/user/signup | signUp : sign up

- *POST* \
/api/user/update | update : update user info


## Models
- CarResponse
- LoginRequest
- LotRequest
- LotResponse
- ResetRequest
- SignUpRequest
- SpaceRequest
- SpaceResponse
- SpecificSpaceRequest
- SpecificSpaceResponse
- StringResponse
- TokenRequest
- TokenResponse
- UserRequest
- UserResponse
