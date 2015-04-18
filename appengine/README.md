
# The Djangae Scaffold Project

This is a barebones Django project configured for use on App Engine using [Djangae](https://github.com/potatolondon/djangae)

To get started:

 - Clone this repo (don't forget to change the origin to your own repo!)
 - Run `./install_deps` (this will pip install requirements, and download the App Engine SDK)
 - `python manage.py checksecure --settings=scaffold.settings_live`
 - `python manage.py runserver`

The install_deps helper script will install dependencies into a 'sitepackages' folder which is added to the path. Each time you run it your
sitepackages will be wiped out and reinstalled with pip. The SDK will only be downloaded the first time (as it's a large download).

## Deployment

Create a Google App Engine project. Edit `app.yaml` and change `application: djangae-scaffold` to `application: your-app-id`. Then, if you're in the `djangae-scaffold` directory, run:

    $ appcfg.py update ./

If you have two-factor authentication enabled in your Google account, run:

    $ appcfg.py --oauth2 update ./

## Custom Domains

There is currently a [bug in App Engine](https://code.google.com/p/googleappengine/issues/detail?id=7427) which means that HSTS headers are stripped from responses served from a custom domain.  If you're using HTTPS on a custom domain then you should make a request to Google to get your domain whitelisted for HSTS.

## Troubleshooting

If you are on OS X and using Homebrew-ed Python, you might get the following error when running `./install_deps`:

    error: must supply either home or prefix/exec-prefix -- not both

[This is a known issue](https://github.com/Homebrew/homebrew/blob/master/share/doc/homebrew/Homebrew-and-Python.md#note-on-pip-install---user) and a possible workaround is to make an "empty prefix" by default by adding a `~/.pydistutils.cfg` file with the following contents:

```bash
[install]
prefix=
```
