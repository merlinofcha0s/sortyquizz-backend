import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserPackService } from 'app/entities/user-pack/user-pack.service';
import { IUserPack, UserPack } from 'app/shared/model/user-pack.model';
import { PackState } from 'app/shared/model/enumerations/pack-state.model';

describe('Service Tests', () => {
  describe('UserPack Service', () => {
    let injector: TestBed;
    let service: UserPackService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserPack;
    let expectedResult: IUserPack | IUserPack[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserPackService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserPack(0, PackState.OPEN, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserPack', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserPack()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserPack', () => {
        const returnedFromService = Object.assign(
          {
            state: 'BBBBBB',
            lifeLeft: 1,
            nbQuestionsToSucceed: 1,
            timeAtQuizzStep: 1,
            timeAtSortingStep: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserPack', () => {
        const returnedFromService = Object.assign(
          {
            state: 'BBBBBB',
            lifeLeft: 1,
            nbQuestionsToSucceed: 1,
            timeAtQuizzStep: 1,
            timeAtSortingStep: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserPack', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
