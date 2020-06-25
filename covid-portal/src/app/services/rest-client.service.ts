import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginRequestModel} from '../model/login-request-model';
import {UserModel} from '../model/user-model';
import {ConstantsModel} from '../util/constants-model';

@Injectable({
  providedIn: 'root'
})
export class RestClientService {
  HOST_NAME = 'http://localhost:8080';
  // aws host name
  // HOST_NAME = 'http://13.233.94.220:8080';
  AUTHENTICATION_URL = this.HOST_NAME + '/authenticate';
  SIGNUP_URL = this.HOST_NAME + '/signup';
  CHECK_AUTHENTICATION_URL = this.HOST_NAME + '/checkAuthentication';
  AGGREGATED_DATA_ENTITY_URL = this.HOST_NAME + '/region_name/';
  AGGREGATED_DATA_ALL_STATES_URL = this.HOST_NAME + '/region_type/state';
  AGGREGATED_DATA_ALL_DISTRICTS_URL = this.HOST_NAME + '/region_type/';
  CUMULATIVE_DATA_COUNTRY_URL = this.HOST_NAME + '/country/cumulative';
  CUMULATIVE_DATA_STATE_URL = this.HOST_NAME + '/state/cumulative/';
  CUMULATIVE_DATA_DISTRICT_URL = this.HOST_NAME + '/district/cumulative/';
  GET_USERNAME_URL = this.HOST_NAME + '/get/name';
  constructor(private http: HttpClient) {
  }

  public getUserName(token) {
    const tokenStr = this.getAuthprizationHeaderString(token);
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get(this.GET_USERNAME_URL, {headers, responseType: 'text' as 'json'});
  }

  public generateToken(request: LoginRequestModel) {
    return this.http.post(this.AUTHENTICATION_URL, request, {responseType: 'text' as 'json'});
  }

  public checkAuthentication(token) {
    const tokenStr = this.getAuthprizationHeaderString(token);
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get(this.CHECK_AUTHENTICATION_URL, {headers, responseType: 'text' as 'json'});
  }

  public signUp(user: UserModel) {
    return this.http.post(this.SIGNUP_URL, user, {responseType: 'text' as 'json'});
  }

  public getAggregatedDataSingleEntity(token, name) {
    const tokenStr = this.getAuthprizationHeaderString(token);
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get(this.AGGREGATED_DATA_ENTITY_URL + name, {headers, responseType: 'text' as 'json'});
  }

  public getCumulativeData(token, name, type) {
    const tokenStr = this.getAuthprizationHeaderString(token);
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    if (type === ConstantsModel.REGION_TYPE_DISTRICT) {
      return this.http.get(this.CUMULATIVE_DATA_DISTRICT_URL + name, {headers, responseType: 'text' as 'json'});
    } else if (type === ConstantsModel.REGION_TYPE_STATE) {
      return this.http.get( this.CUMULATIVE_DATA_STATE_URL + name, {headers, responseType: 'text' as 'json'});
    } else {
      return this.http.get(this.CUMULATIVE_DATA_COUNTRY_URL, {headers, responseType: 'text' as 'json'});
    }
  }

  public getAggregatedDataSubRegion(token, name, regionType) {
    const tokenStr = this.getAuthprizationHeaderString(token);
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    if (regionType === ConstantsModel.REGION_TYPE_COUNTRY) {
      return this.http.get(this.AGGREGATED_DATA_ALL_STATES_URL, {headers, responseType: 'text' as 'json'});
    } else if (regionType === ConstantsModel.REGION_TYPE_STATE) {
      return this.http.get(this.AGGREGATED_DATA_ALL_DISTRICTS_URL + name + '/allcities', {headers, responseType: 'text' as 'json'});
    }
  }

  private getAuthprizationHeaderString(token): string {
    return 'Bearer ' + token;
  }
}
