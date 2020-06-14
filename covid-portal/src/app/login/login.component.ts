import {Component, OnInit} from '@angular/core';
import {RestClientService} from '../services/rest-client.service';
import {LoginRequestModel} from '../model/login-request-model';
import {UserModel} from '../model/user-model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  request: LoginRequestModel;
  userModel: UserModel;
  tokenString: string;
  isAuthenticated: boolean;
  activeNavLink: string = 'Home';
  acceptTerms: boolean = false;

  constructor(private service: RestClientService) {
  }

  ngOnInit() {
    this.request = new LoginRequestModel();
  }

  setActiveTab(tab: string) {
    this.activeNavLink = tab;
  }

  public getAccessToken() {
    let resp = this.service.generateToken(this.request);
    resp.subscribe(data => {
      console.log("Token: " + data);
      this.tokenString = JSON.stringify(data);
      this.activeNavLink = 'Covid';
      this.isAuthenticated = true;
    });
  }

  signUp() {

  }

  logout() {
    this.tokenString = undefined;
    this.isAuthenticated = false;
    this.activeNavLink = 'Home';
  }
}
