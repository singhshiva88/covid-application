import {Component, OnInit} from '@angular/core';
import {LoginRequestModel} from '../model/login-request-model';
import {UserModel} from '../model/user-model';
import {ConstantsModel} from '../util/constants-model';
import {RestClientService} from '../services/rest-client.service';
import {AppCookieService} from '../AppCookieService/app-cookie-service.service';

@Component({
  selector: 'app-landing-controller',
  templateUrl: './landing-controller.component.html',
  styleUrls: ['./landing-controller.component.css', '../shared/common.css']
})
export class LandingControllerComponent implements OnInit {


  request: LoginRequestModel;
  userModel: UserModel = new UserModel();
  tokenString: string;
  isAuthenticated: boolean;
  activeNavLink = ConstantsModel.HOME_TAB;
  acceptTerms = false;
  userName: string;

  // Success and Error messages
  signupError = '';
  loginErrorString = '';

  constructor(private service: RestClientService, private cookie: AppCookieService) {
  }

  ngOnInit() {
    this.request = new LoginRequestModel();
    this.tokenString = this.cookie.get(ConstantsModel.SECTRET_KEY);
    if (this.tokenString) {
      const resp = this.service.checkAuthentication(this.tokenString);
      resp.subscribe(data => {
          this.cookie.set(ConstantsModel.SECTRET_KEY, this.tokenString);
          this.activeNavLink = ConstantsModel.COVID_TAB;
          this.isAuthenticated = true;
          this.setUserName();
        },
        err => {
          this.tokenString = undefined;
          this.cookie.remove(ConstantsModel.SECTRET_KEY);
          this.activeNavLink = ConstantsModel.HOME_TAB;
          this.isAuthenticated = false;
          this.userName = '';
        });
    }
  }

  setActiveTab(tab: string) {
    this.activeNavLink = tab;
  }

  public login() {
    const resp = this.service.generateToken(this.request);
    resp.subscribe(data => {
        console.log('Token: ' + data);
        this.tokenString = data.toString();
        this.cookie.set(ConstantsModel.SECTRET_KEY, this.tokenString);
        this.activeNavLink = ConstantsModel.COVID_TAB;
        this.isAuthenticated = true;
        this.loginErrorString = '';
        this.setUserName();
      },
      error1 => {
        this.loginErrorString = 'Invalid username or password';
      });
  }

  signUp() {
    const resp = this.service.signUp(this.userModel);
    resp.subscribe(data => {
        this.signupError = '';
        this.request.username = this.userModel.username;
        this.request.password = this.userModel.password;
        this.login();
      },
      error1 => {
        this.signupError = 'A user with this name already exists';
      });
  }

  logout() {
    this.cookie.remove(ConstantsModel.SECTRET_KEY);
    this.tokenString = undefined;
    this.isAuthenticated = false;
    this.activeNavLink = ConstantsModel.HOME_TAB;
    this.userName = '';
  }

  setUserName() {
    if (this.tokenString) {
      const resp = this.service.getUserName(this.tokenString);
      resp.subscribe(data => {
        this.userName = data.toString();
      });
    }
  }
}
