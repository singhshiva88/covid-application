import {Component, OnInit} from '@angular/core';
import {RestClientService} from '../services/rest-client.service';
import {LoginRequestModel} from '../model/login-request-model';
import {UserModel} from '../model/user-model';
import {AppCookieService} from '../AppCookieService/app-cookie-service.service';
import {ConstantsModel} from '../util/constants-model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  request: LoginRequestModel;
  userModel: UserModel = new UserModel();
  tokenString: string;
  isAuthenticated: boolean;
  activeNavLink = ConstantsModel.HOME_TAB;
  acceptTerms = false;

  // Success and Error messages
  private successSignUpMsg: string;
  private signupError: string;
  private loginErrorString: string;

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
        },
        err => {
          this.tokenString = undefined;
          this.cookie.remove(ConstantsModel.SECTRET_KEY);
          this.activeNavLink = ConstantsModel.HOME_TAB;
          this.isAuthenticated = false;
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
    },
      error1 => {
      this.loginErrorString = 'Invalid username or password';
      });
  }

  signUp() {
    const resp = this.service.signUp(this.userModel);
    resp.subscribe(data => {
        this.successSignUpMsg = 'Sign Up Complete'
    },
      error1 => {
        this.signupError = 'A user with this name already exists';
      });
  }

  logout() {
    this.tokenString = undefined;
    this.isAuthenticated = false;
    this.activeNavLink = ConstantsModel.HOME_TAB;
  }
}
