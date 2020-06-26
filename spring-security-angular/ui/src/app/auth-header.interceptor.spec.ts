import { TestBed } from '@angular/core/testing';

import { AuthHeaderInterceptor } from './auth-header.interceptor';

describe('AuthHeaderInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      AuthHeaderInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: AuthHeaderInterceptor = TestBed.inject(AuthHeaderInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
