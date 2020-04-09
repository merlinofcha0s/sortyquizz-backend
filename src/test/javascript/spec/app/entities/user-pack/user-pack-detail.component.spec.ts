import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SortyquizzTestModule } from '../../../test.module';
import { UserPackDetailComponent } from 'app/entities/user-pack/user-pack-detail.component';
import { UserPack } from 'app/shared/model/user-pack.model';

describe('Component Tests', () => {
  describe('UserPack Management Detail Component', () => {
    let comp: UserPackDetailComponent;
    let fixture: ComponentFixture<UserPackDetailComponent>;
    const route = ({ data: of({ userPack: new UserPack(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SortyquizzTestModule],
        declarations: [UserPackDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserPackDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserPackDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userPack on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userPack).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
