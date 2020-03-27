import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SortyquizzTestModule } from '../../../test.module';
import { PackDetailComponent } from 'app/entities/pack/pack-detail.component';
import { Pack } from 'app/shared/model/pack.model';

describe('Component Tests', () => {
  describe('Pack Management Detail Component', () => {
    let comp: PackDetailComponent;
    let fixture: ComponentFixture<PackDetailComponent>;
    const route = ({ data: of({ pack: new Pack(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SortyquizzTestModule],
        declarations: [PackDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PackDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PackDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pack on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pack).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
