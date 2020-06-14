import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginRequestModel} from '../model/login-request-model';
import {UserModel} from '../model/user-model';

@Injectable({
  providedIn: 'root'
})
export class RestClientService {

  constructor(private http: HttpClient) {
  }

  public generateToken(request: LoginRequestModel) {
    return this.http.post("http://localhost:8080/authenticate", request, {responseType: 'text' as 'json'})
  }

  public home(token) {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get("http://localhost:8080/authenticate", {headers, responseType: 'text' as 'json'});
  }
  
  public signUp(token, user: UserModel) {
    return this.http.post("http://localhost:8080/authenticate", user, {responseType: 'text' as 'json'});
  }
}
