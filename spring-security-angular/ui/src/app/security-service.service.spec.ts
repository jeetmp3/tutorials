import { TestBed } from '@angular/core/testing';

import { SecurityServiceService } from './security-service.service';

describe('SecurityServiceService', () => {
  let service: SecurityServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecurityServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
