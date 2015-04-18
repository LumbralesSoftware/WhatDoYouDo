from django.conf import settings

def check_session_csrf_enabled():
    if "session_csrf.CsrfMiddleware" not in settings.MIDDLEWARE_CLASSES:
        return [ "SESSION_CSRF_DISABLED"]

    return []
check_session_csrf_enabled.messages = { "SESSION_CSRF_DISABLED" : "Please add 'session_csrf.CsrfMiddleware' to MIDDLEWARE_CLASSES" }


def check_csp_is_not_report_only():
    if getattr(settings, "CSP_REPORT_ONLY", False):
        return ["CSP_REPORT_ONLY_ENABLED"]
    return []
check_csp_is_not_report_only.messages = { "CSP_REPORT_ONLY_ENABLED" : "Please set 'CSP_REPORT_ONLY' to False" }


CSP_SOURCE_NAMES = ['CSP_DEFAULT_SRC', 'CSP_SCRIPT_SRC', 'CSP_IMG_SRC', 'CSP_OBJECT_SRC'
                    'CSP_MEDIA_SRC', 'CSP_FRAME_SRC', 'CSP_FONT_SRC', 'CSP_STYLE_SRC', 'CSP_CONNECT_SRC']

def check_csp_sources_not_unsafe():
    messages = []
    for csp_src_name in CSP_SOURCE_NAMES:
        csp_src_values = getattr(settings, csp_src_name, [])
        if "'unsafe-inline'" in csp_src_values or "'unsafe-eval'" in csp_src_values:
            messages.append(csp_src_name + "_UNSAFE")
    return messages
check_csp_sources_not_unsafe.messages = { src + "_UNSAFE": "Please remove 'unsafe-inline'/'unsafe-eval' from " + src for src in CSP_SOURCE_NAMES }
