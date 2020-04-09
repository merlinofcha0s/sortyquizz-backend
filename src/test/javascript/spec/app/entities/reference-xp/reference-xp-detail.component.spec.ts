import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SortyquizzTestModule } from '../../../test.module';
import { ReferenceXPDetailComponent } from 'app/entities/reference-xp/reference-xp-detail.component';
import { ReferenceXP } from 'app/shared/model/reference-xp.model';

describe('Component Tests', () => {
  describe('ReferenceXP Management Detail Component', () => {
    let comp: ReferenceXPDetailComponent;
    let fixture: ComponentFixture<ReferenceXPDetailComponent>;
    const route = ({ data: of({ referenceXP: new ReferenceXP(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SortyquizzTestModule],
        declarations: [ReferenceXPDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReferenceXPDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReferenceXPDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load referenceXP on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.referenceXP).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
