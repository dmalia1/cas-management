import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {finalize} from 'rxjs/operators';
import {SpinnerService, OAuthRegisteredService} from 'mgmt-lib';
import {OAuthService} from '../core/oauth.service';
import {OAuthAttributeReleasePolicy} from '../../../../mgmt-lib/src/lib/domain/attribute-release';

@Injectable({
  providedIn: 'root'
})
export class OAuthFormResolve implements Resolve<OAuthRegisteredService> {

  constructor(private service: OAuthService, private spinner: SpinnerService) {

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OAuthRegisteredService> | OAuthRegisteredService {
    const param: number = +route.params['id'];
    if (param < 0) {
      const srv = new OAuthRegisteredService();
      srv.attributeReleasePolicy = new OAuthAttributeReleasePolicy();
      return srv;
    }
    this.spinner.start('Loading service');
    return this.service.getService(param).pipe(finalize(() => this.spinner.stop()));
  }
}
