import { TestBed } from '@angular/core/testing';

import { AppCookieServiceService } from './app-cookie-service.service';

describe('AppCookieServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppCookieServiceService = TestBed.get(AppCookieServiceService);
    expect(service).toBeTruthy();
  });
});
